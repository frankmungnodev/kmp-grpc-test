## KMP Test GRPC

### This project use Wire
[Wire](https://github.com/square/wire)

### Generate Kotlin Code From Proto
```bash
./gradlew generateCommonMainProtos
```

### Generate Swift Code From Proto
```bash
java -jar ./wire_compiler/Pods/WireCompiler/compiler.jar \
  "--proto_path=./composeApp/src/proto" \
  "--swift_out=./iosApp/iosApp/grpc/generated" \
  "--experimental-module-manifest=./wire_compiler/manifest.yaml"
```