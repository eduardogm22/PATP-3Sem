from http import HTTPStatus

from fastapi import APIRouter

from schemas import UserList, UserLoginSchema, UserPublic, UserSchema
from services import create_users, read_users, update_users

router = APIRouter()


@router.post('/login')
def validate_user(userLogin: UserLoginSchema):
    pass


@router.post('/users/', status_code=HTTPStatus.CREATED, response_model=UserPublic)
def create_users_route(user: UserSchema):
    return create_users(user)


@router.get('/users/', response_model=UserList)
def read_users_route():
    return read_users()


@router.put('/users/{idusuario}', response_model=UserPublic)
def update_users_route(idusuario: int, user: UserSchema):
    return update_users(idusuario, user)
