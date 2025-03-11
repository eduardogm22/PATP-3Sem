from datetime import datetime
from fastapi import status


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

    assert response.status_code == status.HTTP_201_CREATED
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

    assert response.status_code == status.HTTP_200_OK
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


def teste_read_one_user_deve_retornar_200_UserPublic(client):
    response = client.get('/users/1')
    assert response.status_code == status.HTTP_200_OK
    assert response.json() == {
        'idusuario': 1,
        'usuario': 'testuser',
        'nome': 'testename',
        'email': 'test@test.com',
        'data_criacao': datetime.now().isoformat()[:19],
        'idcargo': 4,
    }


def teste_read_one_user_erro_404(client):
    response = client.get('/users/2')
    assert response.status_code == status.HTTP_404_NOT_FOUND
    assert response.json() == {'detail': 'Usuário não encontrado!'}


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

    assert response.status_code == status.HTTP_200_OK
    assert response.json() == {
        'idusuario': 1,
        'usuario': 'putuser',
        'nome': 'putname',
        'email': 'test@put.com',
        'data_criacao': datetime.now().isoformat()[:19],
        'idcargo': 4,
    }


def teste_update_user_erro_404(client):
    response = client.put(
        '/users/2',
        json={
            'usuario': 'putuser',
            'senha': 'putpassword',
            'nome': 'putname',
            'email': 'test@put.com',
            'idcargo': 4,
        },
    )
    assert response.status_code == status.HTTP_404_NOT_FOUND
    assert response.json() == {'detail': 'Usuário não encontrado!'}


def teste_delete_user_deve_retornar_No_Content(client):
    response = client.delete('/users/1')
    assert response.status_code == status.HTTP_204_NO_CONTENT


def teste_delete_user_erro_404(client):
    response = client.delete('/users/2')
    assert response.status_code == status.HTTP_404_NOT_FOUND
    assert response.json() == {'detail': 'Usuário não encontrado!'}
