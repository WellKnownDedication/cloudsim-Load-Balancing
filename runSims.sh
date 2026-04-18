mvn clean compile -pl modules/cloudsim-simulations/

mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp1.baselineSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp1.ABCSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp1.GASimulation

mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp2.baselineSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp2.ABCSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp2.GASimulation

mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp3.baselineSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp3.ABCSimulation
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.exp3.GASimulation

