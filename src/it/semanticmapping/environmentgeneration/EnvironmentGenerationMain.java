package it.semanticmapping.environmentgeneration;

import java.awt.EventQueue;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.RealVar;

public class EnvironmentGenerationMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model("Environment Generation");
		System.out.println(model.getName());

		int w=500;
		int h=500;
		int x_size=5;
		int y_size=5;
		int n=4;
		
		RealVar[] X = model.realVarArray("X",n, 0, x_size, 10.0d);
		RealVar[] Y = model.realVarArray("Y",n, 0, y_size, 10.0d);
		RealVar[] Z = model.realVarArray("Z",n, 0, Math.PI*3/2, Math.PI*3/2);
		
		X[2] = model.realVar(4);
		Y[2] = model.realVar(0);
		Z[2] = model.realVar(Math.PI);

		X[3] = model.realVar(0);
		Y[3] = model.realVar(3);
		Z[3] = model.realVar(0);

		//A 
		model.post(model.realIbexGenericConstraint("{0}={1}+sin({2})", Y[0],Y[2],Z[2]));
		model.post(model.realIbexGenericConstraint("{0}={1}-sin({2})", X[0],X[3],Z[3]));
		model.post(model.realIbexGenericConstraint("{0}={1}", Z[0], Z[3]));

		//B
		model.post(model.realIbexGenericConstraint("{0}={1}+sin({2})", Y[1],Y[3],Z[3]));
		model.post(model.realIbexGenericConstraint("{0}={1}-sin({2})", X[1],X[2],Z[2]));
		model.post(model.realIbexGenericConstraint("{0}={1}", Z[1], Z[2]));

		double[][] x_sol = new double[n][2];
		double[][] y_sol = new double[n][2];
		double[][] z_sol = new double[n][2];
		
		double[][] shapes = new double[n][3];
		Solution solution = new Solution(model);
		model.getSolver().findSolution();
		if(solution != null){
			
			for(int i=0; i < n; i++) {
				x_sol[i] = solution.getRealBounds(X[i]);
				y_sol[i] = solution.getRealBounds(Y[i]);
				z_sol[i] = solution.getRealBounds(Z[i]);
			}

			for(int i=0; i < n; i++) {
				double[] shape = new double[3];
				//shape[0] = (x_sol[i][0]+x_sol[i][1])/2.0d;
				//shape[1] = (y_sol[i][0]+y_sol[i][1])/2.0d;
				//shape[2] = (z_sol[i][0]+z_sol[i][1])/2.0d;
				shape[0] = x_sol[i][0];
				shape[1] = y_sol[i][0];
				shape[2] = z_sol[i][0];
				shapes[i] = shape;
			}
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<3; j++) {
				System.out.print(shapes[i][j] + " ");
			}
			System.out.print("\n");
		}

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				BasicShapes ex = new BasicShapes(w,h,x_size,y_size,n,shapes);
				ex.setVisible(true);
			}
		});
	}
	
	public void addConstraint(int type, int dep_obj, int ind_obj) {
		
	}
}
