from fastapi import APIRouter, Depends, status, HTTPException
from fastapi.responses import JSONResponse
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm import Session
from typing import List

from schemas import UserLoginSchema, UserSchema, UserPublicSchema
from services import AuthUseCases, UserUseCases
from dependecies import get_db_session, token_verifier

login_router = APIRouter()
router = APIRouter(dependencies=[Depends(token_verifier)])

#Constantes dos cargos
ADMIN = 1

@login_router.post('/login')
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
    user_payload: UserSchema, 
    session: Session = Depends(get_db_session),
    user_token_data: dict = Depends(token_verifier)
):
    if user_token_data['role'] != ADMIN:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Acesso negado! Sem permissão para acessar esse recurso.'
        )
    usr = UserUseCases(db_session=session)
    return JSONResponse(
        content=usr.create_users(user_payload),
        status_code=status.HTTP_201_CREATED,
    )


@router.get('/users/', response_model=List[UserPublicSchema])
def read_users_route(
    session: Session = Depends(get_db_session),
    user_token_data: dict = Depends(token_verifier)
):
    if user_token_data['role'] != ADMIN:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Acesso negado! Sem permissão para acessar esse recurso.')
    usr = UserUseCases(db_session=session)
    return usr.read_users()


# @router.get('/users/{idusuario}', response_model=UserPublicSchema)
# def read_one_user_route(idusuario: int):
#     return usr.read_one_user(idusuario)


# @router.put('/users/{idusuario}', response_model=UserPublicSchema)
# def update_users_route(idusuario: int, user: UserSchema):
#     return usr.update_users(idusuario, user)


# @router.delete('/users/{idusuario}', status_code=status.HTTP_204_NO_CONTENT)
# def delete_user_route(idusuario: int):
#     return usr.delete_users(idusuario)
