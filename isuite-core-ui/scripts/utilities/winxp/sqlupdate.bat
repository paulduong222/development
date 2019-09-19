set DB=B:\e-ISuite\app\webapps\isuite\utilities\db\
set QUEUE=B:\e-ISuite\app\webapps\isuite\utilities\sqlqueue\
set PROC=B:\e-ISuite\app\webapps\isuite\utilities\sqlprocessed
set EXEC="C:\Program Files\NWCG\e-ISuite\pgsql\bin\psql.exe"

subst b: "C:\Documents and Settings\All Users\Application Data"

for %%d in (%DB%*) do (for %%f in (%QUEUE%*) do (%EXEC% -h 127.0.0.1 -U isw -d %%~nxd -a -f %%f))

move /Y %QUEUE%\* %PROC%

del /q %DB%

subst b: /D







