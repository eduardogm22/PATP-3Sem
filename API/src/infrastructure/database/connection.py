from decouple import config
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

 
#DB_URL = config('POSTGRES_DRIVER') + '://' + config('POSTGRES_HOST_NAME') + '/' + config('POSTGRES_NAME') + '?user=' + config('POSTGRES_USER') + '&password=' + config('POSTGRES_PASSWORD')

#DB_URL = 'mysql+pymysql://root:1404@localhost:3306/db_assets_control'

DB_URL = config('MYSQL_DRIVER') + '://' + config('MYSQL_USER') + ':' + config('MYSQL_PASSWORD') + '@' + config('MYSQL_HOSTNAME') + ':' + config('MYSQL_PORT') + '/' + config('MYSQL_DB_NAME')

engine = create_engine(DB_URL, pool_pre_ping=True)

Session = sessionmaker(bind=engine)


def get_db_session():
    try:
        session = Session()
        yield session
    finally:
        session.close()
