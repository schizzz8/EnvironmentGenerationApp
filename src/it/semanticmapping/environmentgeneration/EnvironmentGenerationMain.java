package it.semanticmapping.environmentgeneration;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.RealVar;

public class EnvironmentGenerationMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model("Environment Generation");
		System.out.println(model.getName());

		
		//A
		RealVar x_a = model.realVar("X_a", 0, 2, 0.0001d);
		RealVar y_a = model.realVar("Y_a", 0, 2, 0.0001d);
		RealVar z_a = model.realVar("Z_a", 0, Math.PI*3/2, Math.PI/2);

		//B
		RealVar x_b = model.realVar("X_b", 0, 2, 0.0001d);
		RealVar y_b = model.realVar("Y_b", 0, 2, 0.0001d);
		RealVar z_b = model.realVar("Z_b", 0, Math.PI*3/2, Math.PI/2);
		
		RealVar cost = model.realVar(0);

		model.post(model.realIbexGenericConstraint("{0}={1}", z_a,cost));
		model.post(model.realIbexGenericConstraint("{0}={1}+cos({2})", x_b,x_a,z_a));
		model.post(model.realIbexGenericConstraint("{0}={1}+sin({2})", y_b,y_a,z_a));
		model.post(model.realIbexGenericConstraint("{0}={1}", z_b, z_a));

		Solution solution = model.getSolver().findSolution();
		if(solution != null){
			System.out.println(solution.toString());
		}
	}

}
