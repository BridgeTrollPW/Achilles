# Achilles

## Development with vscode
Add to .classpath
```
    <classpathentry kind="lib" path="/home/luis/javafx-sdk-15.0.1/lib/javafx.base.jar"/>
    <classpathentry kind="lib" path="/home/luis/javafx-sdk-15.0.1/lib/javafx.controls.jar"/>
    <classpathentry kind="lib" path="/home/luis/javafx-sdk-15.0.1/lib/javafx.fxml.jar"/>
    <classpathentry kind="lib" path="/home/luis/javafx-sdk-15.0.1/lib/javafx.graphics.jar"/>
```
Add launch config to launch.json
Replace "/home/luis/javafx-sdk-15.0.1/lib" with the path to your local javafx-sdk, see openjfx.io
```
        {
            "type": "java",
            "name": "Launch Client",
            "request": "launch",
            "mainClass": "net.gat3way.achilles.Main",
            "projectName": "client",
            "vmArgs": "--module-path /home/luis/javafx-sdk-15.0.1/lib --add-modules javafx.controls,javafx.fxml",
            "cwd": "${workspaceFolder}/client"
        }
```

## Third-Party
* https://openjfx.io GPL 2