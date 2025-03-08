from datetime import datetime

from pydantic import BaseModel, EmailStr


class UserSchema(BaseModel):
    idusuario: int
    usuario: str
    senha: str
    nome: str
    email: EmailStr
    data_criacao: datetime
    idcargo: int


class UserPublic(BaseModel):
    idusuario: int
    usuario: str
    nome: str
    email: EmailStr
    data_criacao: datetime
    idcargo: int


class UserLoginSchema(BaseModel):
    idusuario: int
    login: str
    senha: str
