from datetime import datetime
from typing import Optional

from sqlalchemy import func
from sqlalchemy.orm import Mapped, mapped_column, registry

table_registry = registry()


@table_registry.mapped_as_dataclass
class UserModel:
    __tablename__ = 'users'
    userid: Mapped[Optional[int]] = mapped_column(init=False, primary_key=True)
    username: Mapped[str] = mapped_column(unique=True, nullable=False)
    passwd: Mapped[str] = mapped_column(nullable=False)
    person_name: Mapped[str]
    email: Mapped[str]
    roleid: Mapped[int] = mapped_column(nullable=False)
    created_at: Mapped[datetime] = mapped_column(
        server_default=func.now(), default=datetime.now()
    )
