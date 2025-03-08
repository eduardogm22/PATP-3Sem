from fastapi import APIRouter

from schemas import UserLoginSchema, UserSchema

router = APIRouter()


@router.post('/login')
def validate_user(userLogin: UserLoginSchema):
    pass


@router.post('/users')
def create_users(user: UserSchema):
    return user
