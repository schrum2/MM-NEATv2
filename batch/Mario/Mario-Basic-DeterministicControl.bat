cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:mario trials:1 maxGens:1000 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.mario.MarioTask cleanOldNetworks:true fs:false log:Mario-DeterministicControl saveTo:DeterministicControl watch:false marioInputStartX:-3 marioInputStartY:-2 marioInputWidth:12 marioInputHeight:5 showMarioInputs:false deterministic:true