from datetime import datetime
from http import HTTPStatus


def test_create_user_deve_retornar_UserPublic(client):
    response = client.post(
        '/users/',
        json={
            'usuario': 'testuser',
            'senha': 'testpassword',
            'nome': 'testename',
            'email': 'test@test.com',
            'idcargo': 4,
        },
    )

    assert response.status_code == HTTPStatus.CREATED
    assert response.json() == {
        'idusuario': 1,
        'usuario': 'testuser',
        'nome': 'testename',
        'email': 'test@test.com',
        'data_criacao': datetime.now().isoformat()[:19],
        'idcargo': 4,
    }


def teste_read_users_deve_retornar_lista_de_UserPublic(client):
    response = client.get('/users/')

    assert response.status_code == HTTPStatus.OK
    assert response.json()['users'] == [
        {
            'idusuario': 1,
            'usuario': 'testuser',
            'nome': 'testename',
            'email': 'test@test.com',
            'data_criacao': datetime.now().isoformat()[:19],
            'idcargo': 4,
        }
    ]


def teste_update_user_deve_retornar_UserPublic(client):
    response = client.put(
        '/users/1',
        json={
            'usuario': 'putuser',
            'senha': 'putpassword',
            'nome': 'putname',
            'email': 'test@put.com',
            'idcargo': 4,
        },
    )

    assert response.status_code == HTTPStatus.OK
    assert response.json() == {
        'idusuario': 1,
        'usuario': 'putuser',
        'nome': 'putname',
        'email': 'test@put.com',
        'data_criacao': datetime.now().isoformat()[:19],
        'idcargo': 4
    }
