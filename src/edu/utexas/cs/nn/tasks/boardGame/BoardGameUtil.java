package edu.utexas.cs.nn.tasks.boardGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import boardGame.BoardGame;
import boardGame.BoardGamePlayer;
import boardGame.BoardGameState;
import boardGame.TwoDimensionalBoardGame;
import edu.utexas.cs.nn.networks.hyperneat.Substrate;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.MiscUtil;
import edu.utexas.cs.nn.util.datastructures.Pair;
import edu.utexas.cs.nn.util.datastructures.Triple;

public class BoardGameUtil {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Pair<double[], double[]>> playGame(BoardGame bg, BoardGamePlayer[] players){
		bg.reset();
		while(!bg.isGameOver()){
			if(Parameters.parameters.booleanParameter("stepByStep")){
				System.out.print("Press enter to continue");
				System.out.println(bg.toString());
				MiscUtil.waitForReadStringAndEnterKeyPress();
			}
			bg.move(players[bg.getCurrentPlayer()]);
		}
//		System.out.println("Game over");
//		System.out.println(game);
		
		List<Integer> winners = bg.getWinners();
		ArrayList<Pair<double[], double[]>> scoring = new ArrayList<Pair<double[], double[]>>(bg.getNumPlayers());
		
		for(int i = 0; i < players.length; i++){
		
		double fitness = winners.size() > 1 && winners.contains(i) ? 0 : // multiple winners means tie: fitness is 0 
						(winners.get(0) == i ? 1 // If the one winner is 0, then the neural network won: fitness 1
											 : -2); // Else the network lost: fitness -2
				
		Pair<double[], double[]> evalResults = new Pair<double[], double[]>(new double[] { fitness }, new double[0]);
		scoring.add(evalResults);
		 }
		
		
		return scoring; // Returns the Fitness of the individual's Genotype<T>
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List<Substrate> getSubstrateInformation(BoardGame bg) {
			TwoDimensionalBoardGame temp = (TwoDimensionalBoardGame) bg;
			int height = temp.getStartingState().getBoardHeight();
			int width = temp.getStartingState().getBoardWidth();
			List<Substrate> substrateInformation = new LinkedList<Substrate>();
			Substrate boardInputs = new Substrate(new Pair<Integer, Integer>(width, height), 
			Substrate.INPUT_SUBSTRATE, new Triple<Integer, Integer, Integer>(0, Substrate.INPUT_SUBSTRATE, 0), "Board Inputs");
			substrateInformation.add(boardInputs);
			Substrate processing = new Substrate(new Pair<Integer, Integer>(width, height), 
			Substrate.PROCCESS_SUBSTRATE, new Triple<Integer, Integer, Integer>(0, Substrate.PROCCESS_SUBSTRATE, 0), "Processing");
			substrateInformation.add(processing);
			Substrate output = new Substrate(new Pair<Integer, Integer>(1, 1), // Single utility value
			Substrate.OUTPUT_SUBSTRATE, new Triple<Integer, Integer, Integer>(0, Substrate.OUTPUT_SUBSTRATE, 0), "Utility Output");
			substrateInformation.add(output);
			// Otherwise, no substrates will be defined, and the code will crash from the null result
		
		return substrateInformation;
	}

	// Used for Hyper-NEAT

	public static List<Pair<String, String>> getSubstrateConnectivity() {
			List<Pair<String, String>> substrateConnectivity = new LinkedList<Pair<String, String>>();
			substrateConnectivity.add(new Pair<String, String>("Board Inputs", "Processing"));
			substrateConnectivity.add(new Pair<String, String>("Processing", "Utility Output"));	
			if(Parameters.parameters.booleanParameter("extraHNLinks")) {
				substrateConnectivity.add(new Pair<String, String>("Board Inputs", "Utility Output"));
			}
		
		return substrateConnectivity;
	}
	
}
