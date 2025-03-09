from fastapi import FastAPI

from routes import router

app = FastAPI()
app.include_router(router)


@app.get('/')
def read_root():
    return {
        'version': 'API de Controle de Patrimônio V1.0.0',
        'message': 'Acesse a documentação acessando: http://127.0.0.1:8080/docs',
    }
