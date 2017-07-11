package genetic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.io.*;

import genetic.csv.*;
import genetic.data.*;
import genetic.mutation.*;
import genetic.gene.*;

public class Main
{
	static DistanceTable distanceTable;

	private static class cmp implements Comparator<Gene>
	{
		public int compare(Gene g1, Gene g2)
		{
			Integer i1 = new Integer(g1.getTotalNextDistance());
			return i1.compareTo(new Integer(g2.getTotalNextDistance()));
		}
	}

	public static void main(String[] args) throws Exception
	{
		ClassSystem system = new ClassSystem(args[0]);
		system.selectSemester(2013, 1);


		Gene gene = ClassManager.getInstance().makeGene();
		Random rd = new Random();

		for(int tc = 0; tc< 20; tc++)
		{
			Gene[] tmp = new Gene[400];
			Gene[] generation = new Gene[100];

			for(int i=0; i<1; i++)
			{
				GreedyAdaptor rA = new GreedyAdaptor();
				rA.setGene(gene);
				generation[i] = rA.mutate(100);
				for(int k=0; k<500; k++)
				{
					BufferedWriter out2 = new BufferedWriter(new FileWriter("result/pool"+tc+".csv", true));
					StringBuilder t2 = new StringBuilder();
					GreedyAdaptor rA2 = new GreedyAdaptor();
					rA2.setGene(generation[i]);
					generation[i] = rA2.mutate(100);
					if(k%10 == 0 ) System.out.println("");
					System.out.print("g");
					t2.append(k + "," + generation[i].getTotalNextDistance() + ",\n");

					System.out.println(t2.toString());
					out2.write(t2.toString());
					out2.close();
				}
				if(i%10 == 0 ) System.out.println("");
			}

		}

	}
}
