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

		Gene[] tmp = new Gene[400];
		Gene[] generation = new Gene[100];

		for(int i=0; i<100; i++)
		{
		GreedyAdaptor rA = new GreedyAdaptor();
			rA.setGene(gene);
			generation[i] = rA.mutate(100);
			if(i%10 == 0) System.out.println("");
			System.out.print("g");
		}

		for(int count = 0; count < 100; count++)
		{
		BufferedWriter out = new BufferedWriter(new FileWriter("result/out"+""+".csv", true));
			for(int i=0; i<100; i++)
				tmp[i] = generation[i];
			for(int i=100; i<300; i++)
			{
		MutationAdaptor mA = new MutationAdaptor();
				tmp[i] = mA.mutate(generation[rd.nextInt(100)], generation[rd.nextInt(100)], 50);
			if(i%10 == 0) System.out.println("");
			System.out.print("c");
			}

			for(int i=300; i<400; i++)
			{
				GreedyAdaptor g = new GreedyAdaptor(); g.setGene(tmp[i-300]);
			if(i%10 == 0) System.out.println("");
			System.out.print("g");
				tmp[i] = g.mutate(100);
			}

			System.out.println("sort");
			Arrays.sort(tmp, new cmp());
			System.out.println("sort end");

			HashSet<Integer> hset = new HashSet<Integer>();
			for(int i=0; i<75; i++)
			{
				hset.add(i);
				generation[i] = tmp[i];
			}
			int index = 75;
			while(hset.size() < 100)
			{
				int n = rd.nextInt(400);
				if(hset.contains(n)) continue;
				
				hset.add(n);
				generation[index++] = tmp[n];
			}

			StringBuilder t = new StringBuilder();
			for(Gene _class : generation)
				       t.append(count + "," + _class.getTotalNextDistance() + ",\n");

			System.out.println(t.toString());
			out.write(t.toString());
		out.close();
		}


	}
}
