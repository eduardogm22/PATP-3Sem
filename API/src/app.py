from fastapi import FastAPI
from schemas import UserSchema, UserPublic, UserLoginSchema

app = FastAPI()


@app.get('/')
def teste():
    return {'message': 'teste'}

@app.post('/login')
def validate_user(userLogin : UserLoginSchema):
    pass

@app.post('/users')
def create_users(user : UserSchema):
    return user