from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, Session
from database import get_mysql_db_url
from services import AuthUseCases
from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer


engine = create_engine(get_mysql_db_url(), pool_pre_ping=True)
local_session = sessionmaker(bind=engine)
def get_db_session():
    try:
        session = local_session()
        yield session
    finally:
        session.close()

oauth_scheme = OAuth2PasswordBearer(tokenUrl='/login')
def token_verifier(
    session: Session = Depends(get_db_session),
    token = Depends(oauth_scheme)
):
    auth_uc = AuthUseCases(db_session=session)
    try:
        token_data = auth_uc.validate_token(access_token=token)
    except Exception:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Usuário ou senha inválidos! token data'
        )
    return {'userid':token_data['userid'],
            'userrole':token_data['userrole']}
    