from decouple import config

# DB_URL = config('POSTGRES_DRIVER') + '://'
#   + config('POSTGRES_HOST_NAME') + '/'
#   + config('POSTGRES_NAME')
#   + '?user=' + config('POSTGRES_USER')
#   + '&password='
#   + config('POSTGRES_PASSWORD')

# DB_URL = 'mysql+pymysql://root:1404@localhost:3306/db_assets_control'

def get_mysql_db_url(): 
    return (
        config('MYSQL_DRIVER')
        + '://'
        + config('MYSQL_USER')
        + ':'
        + config('MYSQL_PASSWORD')
        + '@'
        + config('MYSQL_HOSTNAME')
        + ':'
        + config('MYSQL_PORT')
        + '/'
        + config('MYSQL_DB_NAME')
)