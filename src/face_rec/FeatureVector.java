/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package face_rec;

import org.opencv.face.Face;

/**
 *
 * @author HOME
 */
public class FeatureVector {
    private double[] 	featureVector;
	private int 		classification; 
	private Face 		face;
	
	
	public int getClassification() {
		return classification;
	}
	public void setClassification(int classification) {
		this.classification = classification;
	}
	public double[] getFeatureVector() {
		return featureVector;
	}
	public void setFeatureVector(double[] featureVector) {
		this.featureVector = featureVector;
	}
	public Face getFace() {
		return face;
	}
	public void setFace(Face face) {
		this.face = face;
	}
}
