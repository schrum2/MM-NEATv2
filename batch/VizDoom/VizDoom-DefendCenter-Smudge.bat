cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:vizdoomdefendcenter trials:2 maxGens:10 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomDefendCenterTask cleanOldNetworks:true fs:false noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:DefendCenter-Smudge saveTo:Smudge gameWad:freedoom2.wad watch:false stepByStep:false doomEpisodeLength:2100 doomInputPixelSmudge:5 doomSmudgeStat:edu.utexas.cs.nn.util.stats.MostExtreme