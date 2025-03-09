from datetime import datetime

from pydantic import BaseModel, EmailStr


class UserSchema(BaseModel):
    usuario: str
    senha: str
    nome: str
    email: EmailStr
    idcargo: int


class UserDB(UserSchema):
    idusuario: int
    data_criacao: datetime


class UserPublic(BaseModel):
    idusuario: int
    usuario: str
    nome: str
    email: EmailStr
    data_criacao: datetime
    idcargo: int


class UserList(BaseModel):
    users: list[UserPublic]


class UserLoginSchema(BaseModel):
    login: str
    senha: str
