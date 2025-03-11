from sqlalchemy.orm import registry, Mapped, mapped_column
from datetime import datetime

table_registry = registry()

@table_registry.mapped_as_dataclass
class User:
    __tablename__ = 'users'
    userid : Mapped[int] = mapped_column(primary_key=True)
    username : Mapped[str]
    passwd : Mapped[str]
    personname : Mapped[str]
    email : Mapped[str] #ver se pode usar email str do pydantic
    created_at : Mapped[datetime]
    roleid : Mapped[int]