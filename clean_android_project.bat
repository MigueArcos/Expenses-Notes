set location=%cd%
del /q "%location%\*.iml"
del /s /q "%location%\app\*.apk"
rmdir /s /q "%location%\.gradle"
del /s /q "%location%\local.properties"
del "%location%\idea\workspace.xml"
rmdir /s /q "%location%\.idea\libraries"
del /s /q "%location%\.DS_Store"
rmdir /s /q "%location%\build"
rmdir /s /q /f "%location%\captures"
rmdir /s /q "%location%\app\build"