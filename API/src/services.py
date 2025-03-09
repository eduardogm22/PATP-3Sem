from datetime import datetime

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
