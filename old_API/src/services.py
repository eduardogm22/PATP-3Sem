from datetime import datetime, timedelta, timezone

from decouple import config
from fastapi import HTTPException, status
from jose import JWTError, jwt
from passlib.context import CryptContext
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm import Session

from schemas import UserLoginSchema, UserSchema, UserPublicSchema
from models import UserModel

crypt_context = CryptContext(schemes=['argon2'])
# caso trocar a chave, pôr o parametro deprecated='auto'


class AuthUseCases:
    def __init__(self, db_session: Session):
        self.db_session = db_session

        self.SECRET_KEY = config('SECRET_KEY')
        self.ALGORITHM = config('ALGORITHM')

    def auth_user(
        self,
        user_payload: UserLoginSchema,
        token_expires_in_min: int = 30,
        refresh_expires_in_days: int = 1,
    ):
        user_on_db = (
            self.db_session.query(UserModel)
            .filter_by(username=user_payload.username)
            .first()
        )

        if user_on_db is None:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail='Usuário ou senha inválidos!',
            )

        if not crypt_context.verify(user_payload.passwd, user_on_db.passwd):
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail='Usuário ou senha inválidos!',
            )

        token_exp = datetime.now(timezone.utc) + timedelta(
            minutes=token_expires_in_min
        )
        refresh_exp = datetime.now(timezone.utc) + timedelta(
            days=refresh_expires_in_days
        )

        token_payload = {
            'userid': user_on_db.userid,
            'role': user_on_db.roleid,
            'exp': token_exp.timestamp(),
        }

        refresh_payload = {
            'userid': user_on_db.userid,
            'role': user_on_db.roleid,
            'exp': refresh_exp.timestamp(),
        }

        access_token = jwt.encode(
            token_payload, self.SECRET_KEY, self.ALGORITHM
        )
        refresh_token = jwt.encode(
            refresh_payload, self.SECRET_KEY, self.ALGORITHM
        )
        return {
            'access_token': access_token,
            'refresh_token': refresh_token,
            'person_name': user_on_db.person_name,
        }

    def validate_token(self, access_token):
        try:
            # jwt.decode verifica automaticamente se o token
            # expirou. Caso sim, da except
            token_payload = jwt.decode(
                access_token, self.SECRET_KEY, algorithms=[self.ALGORITHM]
            )
        except JWTError:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail='Token de acesso inválido!',
            )
        user_on_db = (
            self.db_session.query(UserModel)
            .filter_by(userid=token_payload['userid'])
            .first()
        )
        if user_on_db is None:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail='Token de acesso inválido!',
            )
        return {'userid':user_on_db.userid,
                'role':user_on_db.roleid}


class UserUseCases:
    def __init__(self, db_session: Session):
        self.db_session = db_session

    def create_users(self, user_payload: UserSchema):
        try:
            user_add = UserModel(
                username=user_payload.username,
                passwd=crypt_context.hash(user_payload.passwd),
                person_name=user_payload.person_name,
                email=user_payload.email,
                roleid=user_payload.roleid,
            )
            self.db_session.add(user_add)
            self.db_session.commit()
            self.db_session.refresh(user_add)
            return {
                'userid': user_add.userid,
                'username': user_add.username,
                'person_name': user_add.person_name,
                'email': user_add.email,
                'roleid': user_add.roleid,
                'created_at': user_add.created_at.timestamp(),
            }
        except IntegrityError:
            self.db_session.rollback()
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail='Nome de usuário já em uso!',
            )

    def read_users(self):
        users_on_db = (self.db_session
            .query(UserModel)
            .order_by(UserModel.userid)
            .all()
            )
        return [UserPublicSchema.model_validate(user) for user in users_on_db]

    # def read_one_user(idusuario):
    #     if idusuario < 0 or idusuario > len(database):
    #         raise HTTPException(status_code=status.HTTP_404_NOT_FOUND,
    # detail='Usuário não encontrado!')
    #     return database[idusuario - 1]

    # def update_users(idusuario: int, user: UserSchema):
    #     if idusuario < 1 or idusuario > len(database):
    #         raise HTTPException(status_code=status.HTTP_404_NOT_FOUND,
    # detail='Usuário não encontrado!')
    #     data_criacao = database[idusuario - 1].data_criacao
    #     usuario_atualizado = UserDB(idusuario=idusuario,
    # data_criacao=data_criacao, **user.model_dump())
    #     database[idusuario - 1] = usuario_atualizado
    #     return usuario_atualizado

    # def delete_users(idusuario):
    #     if idusuario < 1 or idusuario > len(database):
    #         raise HTTPException(status_code=status.HTTP_404_NOT_FOUND,
    # detail='Usuário não encontrado!')
    #     del database[idusuario - 1]
