echo "Libraries to delete ..."
unzip -vl build/outputs/aar/fuelsdkunity-debug.aar  "libs/*"
zip --delete build/outputs/aar/fuelsdkunity-debug.aar "libs/*"
