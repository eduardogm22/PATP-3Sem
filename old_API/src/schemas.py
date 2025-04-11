from datetime import datetime

from pydantic import BaseModel, EmailStr


class UserSchema(BaseModel):
    username: str
    passwd: str
    person_name: str
    email: EmailStr
    roleid: int


class UserPublicSchema(BaseModel):
    userid: int
    username: str
    email: EmailStr
    roleid: int
    created_at: datetime
    
    class Config:
        from_attributes = True


class UserLoginSchema(BaseModel):
    username: str
    passwd: str


class UserAuthorized:
    acess_token: str
    refresh_token: str
    person_name: str


class UserList(BaseModel):
    users: list[UserPublicSchema]
