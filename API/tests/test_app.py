from http import HTTPStatus


def test_read_root_deve_retornar_ok_documentacao(client):
    response = client.get('/')

    assert response.status_code == HTTPStatus.OK
    assert response.json() == {
        'version': 'API de Controle de Patrimônio V1.0.0',
        'message': 'Acesse a documentação acessando: http://127.0.0.1:8080/docs',
    }
