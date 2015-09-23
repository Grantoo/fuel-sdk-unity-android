echo "Libraries to delete ..."
unzip -vl build/outputs/aar/fuelsdkunity.aar  "libs/*"
zip --delete build/outputs/aar/fuelsdkunity.aar "libs/*"
