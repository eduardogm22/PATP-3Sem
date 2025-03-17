from fastapi import APIRouter, Depends, status
from fastapi.responses import JSONResponse
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm import Session

from domain.schemas import UserLoginSchema, UserSchema
from domain.services import AuthUseCases, UserUseCases
from infrastructure.database.connection import get_db_session

router = APIRouter()


@router.post('/login')
def auth_user_route(
    user_payload: OAuth2PasswordRequestForm = Depends(),
    session: Session = Depends(get_db_session),
):
    auth = AuthUseCases(db_session=session)
    user_login_schema = UserLoginSchema(
        username=user_payload.username, passwd=user_payload.password
    )
    return JSONResponse(
        content=auth.auth_user(user_payload=user_login_schema),
        status_code=status.HTTP_200_OK,
    )


@router.post('/users/')
def create_users_route(
    user_payload: UserSchema, session: Session = Depends(get_db_session)
):
    usr = UserUseCases(db_session=session)
    return JSONResponse(
        content=usr.create_users(user_payload),
        status_code=status.HTTP_201_CREATED,
    )


# @router.get('/users/', response_model=UserList)
# def read_users_route():
#     return usr.read_users()


# @router.get('/users/{idusuario}', response_model=UserPublicSchema)
# def read_one_user_route(idusuario: int):
#     return usr.read_one_user(idusuario)


# @router.put('/users/{idusuario}', response_model=UserPublicSchema)
# def update_users_route(idusuario: int, user: UserSchema):
#     return usr.update_users(idusuario, user)


# @router.delete('/users/{idusuario}', status_code=status.HTTP_204_NO_CONTENT)
# def delete_user_route(idusuario: int):
#     return usr.delete_users(idusuario)
