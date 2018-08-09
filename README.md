# Handwritten Number Recognition Neural Network

An artificial Neural Network built from scratch that includes a Perceptron that can model any number of neurons and layers to create a Network of Neurons. The network is trained using the MNIST dataset images of handwritten numbers and the designed Neural Network can be used to predict the handwritten number by the user. The network can also be saved after training and it can be loaded again to directly test the handwritten numbers. The network is tested with the MNIST testing dataset as well. Backpropagation method is used to adjust the weights according to the Target result and Sigmoid function is used as the Activation function. 


### Prerequisites

Eclipse IDE

### Running the application

1. Clone the project to your local directory and Open it in Eclipse from your file system. Run the project as Java Application choosing the Main method present in com.psa.Interface package. 

2. Load MNIST dataset 

3. Train the Neural Network

4. Draw the numbers to be predicted on the Panel

5. You can save the network to a file and load it after restarting the application


## Dataset Information

1. Dataset used for training:

train-images-idx3-ubyte.gz:  training set images (9912422 bytes) 
train-labels-idx1-ubyte.gz:  training set labels (28881 bytes) 

2. Dataset used for testing:

t10k-images-idx3-ubyte.gz:   test set images (1648877 bytes) 
t10k-labels-idx1-ubyte.gz:   test set labels (4542 bytes)

[Link to datasets](http://yann.lecun.com/exdb/mnist/)

## Built With

* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Java Swing Library](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)
* [JUnit for testing](https://junit.org/junit5/)
* [MNIST Dataset](http://yann.lecun.com/exdb/mnist/)


## Authors

* **Rahul Deepak Zore** 

* **Sanchit Chavan** 


## Acknowledgments

* Robin Hillyard - Professor (Program Structure & Algorithms, Northeastern University)
* Akash Jagtap - Teaching Assistant (Program Structure & Algorithms, Northeastern University)
* Harshit Raj - Teaching Assistant (Program Structure & Algorithms, Northeastern University)



