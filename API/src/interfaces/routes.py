from fastapi import APIRouter, status

from domain.schemas import UserList, UserPublic, UserSchema
from domain.services import create_users, delete_users, read_one_user, read_users, update_users

router = APIRouter()


# @router.post('/login')
# def validate_user(userLogin: UserLoginSchema):
#     pass


@router.post('/users/', status_code=status.HTTP_201_CREATED, response_model=UserPublic)
def create_users_route(user: UserSchema):
    return create_users(user)


@router.get('/users/', response_model=UserList)
def read_users_route():
    return read_users()


@router.get('/users/{idusuario}', response_model=UserPublic)
def read_one_user_route(idusuario: int):
    return read_one_user(idusuario)


@router.put('/users/{idusuario}', response_model=UserPublic)
def update_users_route(idusuario: int, user: UserSchema):
    return update_users(idusuario, user)


@router.delete('/users/{idusuario}', status_code=status.HTTP_204_NO_CONTENT)
def delete_user_route(idusuario: int):
    return delete_users(idusuario)
