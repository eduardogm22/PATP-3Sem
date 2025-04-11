Guia para baixar as ferramentas necessárias para lidar na API(Eduardo)
https://fastapidozero.dunossauro.com/01/

Instalar pyenv - rodar comando abaixo, no power shell:
Invoke-WebRequest -UseBasicParsing -Uri "https://raw.githubusercontent.com/pyenv-win/pyenv-win/master/pyenv-win/install-pyenv-win.ps1" -OutFile "./install-pyenv-win.ps1"; &"./install-pyenv-win.ps1"

Após isso, fechar o PowerShell, abrir o vscode, abrir o projeto da api, abrir o terminal integrado no vscode(ctrl + shift + ' (botão das aspas)) e rodar:
pyenv --version  (ver se a instalação ocorreu corretamente)
pyenv install 3.13.2
pyenv local 3.13.2 (para setar a versão do python nesse projeto.
se quiser setar em todos, troque local por global)

pip install pipx
pipx install poetry
pipx ensurepath
feche e abra o vscode

verifique se o terminal está no caminho PATP-3sem_controle_patrimonio_python_e_java\api
(ele abre no PATP-3sem_controle_patrimonio_python_e_java, entao é so dar um cd api no terminal)
rodar poetry install para baixar as libs faltantes

para rodar, abra o terminal integrado e rode:
poetry env activate
task run
(clicar no link http://127.0.0.1:8000 e ver se apareceu message : teste)