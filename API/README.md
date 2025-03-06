Guia para baixar as ferramentas necessárias para lidar na API(Eduardo)
https://fastapidozero.dunossauro.com/01/

comandos devem ser rodados no PowerShell(ctrl + shift + ' (botão das aspas))

Instalar pyenv - rodar comando abaixo:
Invoke-WebRequest -UseBasicParsing -Uri "https://raw.githubusercontent.com/pyenv-win/pyenv-win/master/pyenv-win/install-pyenv-win.ps1" -OutFile "./install-pyenv-win.ps1"; &"./install-pyenv-win.ps1"
Após isso, fechar e abrir o PowerShell e rodar:
pyenv --version  (ver se a instalação ocorreu corretamente)
pyenv install 3.13.2
pyenv local 3.13.2 (para setar a versão do python nesse projeto.
se quiser setar em todos, troque local por global)

pip install pipx
pipx install poetry

para rodar, digitar no terminal:
poetry env activate
task run
(clicar no link http://127.0.0.1:8000 e ver se apareceu message : teste)