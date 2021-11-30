@echo --------------------
@echo  project packaging
@echo --------------------
@call mvn clean package assembly:single -DskipTests=true
@echo "Complete, press any key to exit" & pause >nul
