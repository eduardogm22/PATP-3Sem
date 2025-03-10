from datetime import datetime
from http import HTTPStatus

from fastapi import HTTPException

from schemas import UserDB, UserSchema

database = []


def create_users(user: UserSchema):
    usuario_com_id = UserDB(
        idusuario=len(database) + 1,
        data_criacao=datetime.now().isoformat()[:19],
        **user.model_dump(),
    )
    database.append(usuario_com_id)
    return usuario_com_id


def read_users():
    return {'users': database}


def read_one_user(idusuario):
    if idusuario < 0 or idusuario > len(database):
        raise HTTPException(status_code=HTTPStatus.NOT_FOUND, detail='Usuário não encontrado!')
    return database[idusuario - 1]


def update_users(idusuario: int, user: UserSchema):
    if idusuario < 1 or idusuario > len(database):
        raise HTTPException(status_code=HTTPStatus.NOT_FOUND, detail='Usuário não encontrado!')
    data_criacao = database[idusuario - 1].data_criacao
    usuario_atualizado = UserDB(idusuario=idusuario, data_criacao=data_criacao, **user.model_dump())
    database[idusuario - 1] = usuario_atualizado
    return usuario_atualizado


def delete_users(idusuario):
    if idusuario < 1 or idusuario > len(database):
        raise HTTPException(status_code=HTTPStatus.NOT_FOUND, detail='Usuário não encontrado!')
    del database[idusuario - 1]
