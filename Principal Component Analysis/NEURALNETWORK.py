#!/usr/bin/env python
# coding: utf-8

# ## HW 4
# ## Following are skeleton code for hw4, no numpy version

# In[169]:


"""
Feel free to write other helper functions
And change the parameters/return values of the function
To make them work better
For the first part, you can't use numpy or similar ml libraries but can use utilities such as math
You can also write a class about neural network and put related functions inside it
for loading data still feel free to use numpy
To make things easier, your data can be in numpy form, but 
in the first part you should not use numpy functionality such as matmul or dot or related matrix/vectorization methods
in the second part feel free to use anything in numpy

EXTREMELY IMPORTANT THINGS:
1) do -1 on y to get the correct labels
2) the first value in the weights theta_1 and theta_2 files is the bias

for the skeleton code you don't have to follow it, they are here only for your reference
but if you have no idea where to start you can use them to guide your way
"""

import numpy as np
import time 
import math
# read in data and weights
# since label data is in fact 1-10, we will change them into 0 to 9
X = np.genfromtxt('ps5_data.csv',delimiter=',')
y = np.genfromtxt('ps5_data-labels.csv',delimiter=',')
y = y-1
#Add the entry 1 in each vector of x inorder to accomodate the bias term
X = np.insert(X, 0, 1, axis =1)
W1 = np.genfromtxt('ps5_theta1.csv',delimiter=',')
W2 = np.genfromtxt('ps5_theta2.csv',delimiter=',')
print(X.shape, y.shape, W1.shape, W2.shape)

     
def softmax(yhat):
    # 4.2
    result = []
    total = 0
    for each in yhat:
        total += math.exp(each)
    for each in yhat:
        result.append(math.exp(each)/ total)
    return result

def sigmoid(z):
    return 1/ (1 + math.exp(-z))
     

def toCategorical(y):
    # convert to one hot

    return y_one_hot

class NeuralNetwork:
    def __init__(self):
        self.W1 = None #W1 should be 401 * 25
        self.W2 = None #W2 should be 26 * 10
    def setWeight(self, W1, W2):
        self.W1 = W1
        self.W2 = W2
    def neuronActivation(self,Aprev,W,with_sigmoid=True):
        # 4.1
        #this will take activation of previous layer and the weight of previous layer to this layer
        #to produce a single activation value
        result = []
        for i in range(len(W[0])):
            w = W[:,i]
            sum = 0
            for i in range(len(Aprev)):
                sum += Aprev[i] * w[i]
            sum = sigmoid(sum)
            result.append(sum)
        return result
    
    def final_layer(self, activations):
        # 4.3 activation of the final layer
        activations.insert(0,1)
        #print(len(activations))
        #print(len(self.W2[:,1]))
        probabilities = []
        for i in range(len(self.W2[0])):
            w = self.W2[:,i]
            
            sum = 0
            for i in range(len(activations)):
                sum += activations[i] * w[i]
            probabilities.append(sum)
        probabilities = softmax(probabilities)
        return probabilities
        
    def forward(self,X):
        # 4.4 
        #non-vectorized. Given 1 example, return a prediction
        result = self.neuronActivation(X, self.W1)
        final_result = self.final_layer(result)
        return final_result
        

    def classify(self,X): 
        # 4.5, should use 4.4 forward function
        # will classify an image and return a number
        final_result = self.forward(X)
        max_ = max(final_result)
        idx = final_result.index(max_)
        #print("The prediction is that the image belongs to ", idx)
        return int(idx)
    
    def get_error_rate(self, X, y):
        # 4.6 will give error rate on given X, y
        # you can use python's time
        start_time = time.time()
        error = 0
        for i in range(len(X)):
            if self.classify(X[i]) != y[i]:
                error += 1
        error_rate = error/len(X)
        end_time = time.time()
        return error_rate, end_time-start_time
    def giveCost(self, X, y):
        # 4.7
        # gives the value of the cross entropy cost function
        log_sum = 0
        for i in range(len(X)):
            x = X[i]
            y_ = int(y[i])
            probability = self.forward(x)[y_]
            log_sum += math.log(probability)
        J = -1*log_sum/len(X)
        return J


# ## Run the program and report error rate, time used for classify all X, and cost

# In[153]:


np.zeros((2, 1))


# In[170]:


nn = NeuralNetwork()
nn.setWeight(W1.transpose(), W2.transpose())


#print(nn.get_error_rate(X, y))
error_rate, time_used = nn.get_error_rate(X, y)
cost = nn.giveCost(X, y)
print(error_rate, cost, time_used)


# Error rate, cost and time used should be similar to:
# 
# 0.0248 0.1528 12.4

# ## Following are skeleton code for hw4, numpy vectorized version

# In[191]:


import numpy as np
import time 
# read in data and weights
# since label data is in fact 1-10, we will change them into 0 to 9

X = np.genfromtxt('ps5_data.csv',delimiter=',')
y = np.genfromtxt('ps5_data-labels.csv',delimiter=',')
y = y-1
W1 = np.genfromtxt('ps5_theta1.csv',delimiter=',')
W2 = np.genfromtxt('ps5_theta2.csv',delimiter=',')
print(X.shape, y.shape, W1.shape, W2.shape)


# In[192]:


## 4.2 softmax and sigmoid activation
def softmax(X):
    total_value = np.array([np.sum(np.exp(X), axis = 1)])
    return np.divide(np.exp(X), total_value.T)
def sigmoid(X):
    return 1/ (1 + np.exp(-X))
    
def toCategorical(y):
    # convert to one hot
    pass
    #return y_one_hot


# In[221]:


class NeuralNetwork:
    def __init__(self):
        self.W1 = None #W1 should be 401 * 25
        self.W2 = None #W2 should be 26 * 10
    def setWeight(self, W1, W2):
        self.W1 = W1
        self.W2 = W2

    def AddBias(self, X):
        #will add the bias term to the input matrix
        new_x = np.insert(X, 0, 1, axis =1)

        return new_x
    def hidden_layer_activation(self, X, W):
        ## 4.1
        ## use sigmoid activation for hidden layers
        new_x = self.AddBias(X)
        return sigmoid(np.dot(new_x, W))
        
    def final_layer_activation(self, X, W):
        ## 4.3
        ## only applies to final layer
        ## note you only use softmax, don't use sigmoid here
        new_x = self.AddBias(X)
        return softmax(np.dot(new_x, W))
         
    def forward(self, X):
        ## 4.4, should use 4.1, 4.3 functions
        result = self.hidden_layer_activation(X, self.W1)
        #print(result)
        probabilities = self.final_layer_activation(result, self.W2)
        return probabilities 
    def classify(self,X): 
        # 4.5, should use 4.4 forward function
        # will classify an image and return a number
        probabilities = self.forward(X)
        #print(probabilities)
        return np.argmax(probabilities, axis = 1)
    def get_error_rate(self, X, y):
        # 4.6 will give error rate on given X, y
        start_time = time.time()
        error = 0
        #print(self.classify(X))
        non_error = np.count_nonzero(self.classify(X) == y)
        #print(non_error)
        error_rate = (len(X) - non_error) / len(X)
        end_time = time.time()
        return error_rate, end_time-start_time
    def giveCost(self, X, y):
        # 4.7
        # gives the value of the cross entropy cost function
        #np.sum(self.forward(X)[numpy.rint(y)], axis = 1)
        #print(self.forward(X))
        #print(self.forward(X).shape)
        #print(y.T.shape)
        #print(np.rint(y))
        #return self.forward(X)[y]
        #print(len(y))
        nb_classes = 10
        targets = np.array(y.astype(int)).reshape(-1)
        one_hot_target = np.eye(nb_classes)[targets]
        
        #print(np.sum(np.log10(np.sum(self.forward(X) * one_hot_target, axis = 1))) * (-1/ 5000))
        return (-1/len(y)) * (np.sum(np.log(np.sum(self.forward(X) * one_hot_target, axis = 1))))
        
        
        
        


# ## Run the program and report error rate, time used for classify all X, and cost

# In[222]:


nn = NeuralNetwork()
nn.setWeight(W1.transpose(), W2.transpose())
error_rate, time_used = nn.get_error_rate(X, y)
cost = nn.giveCost(X, y)
print(cost)
print(error_rate, time_used)


# Error rate, cost and time used should be similar to:
# 
# 0.0248 0.1528 0.03

# ## Is vectorization making things faster?
