from datetime import datetime
from typing import Optional

from sqlalchemy import String
from sqlalchemy.orm import Mapped, mapped_column, registry

table_registry = registry()


@table_registry.mapped_as_dataclass
class UserModel:
    __tablename__ = 'users'
    userid: Mapped[Optional[int]] = mapped_column(
        init=False, primary_key=True)
    username: Mapped[str] = mapped_column(
        String(50), unique=True, nullable=False)
    passwd: Mapped[str] = mapped_column(
        String(255), nullable=False)
    person_name: Mapped[str] = mapped_column(
        String(150))
    email: Mapped[str] = mapped_column(
        String(150))
    roleid: Mapped[int] = mapped_column(
        nullable=False)
    created_at: Mapped[datetime] = mapped_column(
        default=datetime.now())
