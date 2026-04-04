mvn clean compile -pl modules/cloudsim-simulations/

# ABC datacenter brocker
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.ABCSimulation

# # Baseline with built-in datacenter brokers
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.baselineSimulation

# # GeneticAlgorythm datacenter Brockers
mvn exec:java -pl modules/cloudsim-simulations/ -Dexec.mainClass=environments.GASimulation
