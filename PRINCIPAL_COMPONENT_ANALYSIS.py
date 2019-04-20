import numpy as np
from numpy import linalg as LA
import matplotlib.pyplot as plt

######### Load the data ##########

infile = open('faces.csv','r')
img_data = infile.read().strip().split('\n')
img = [map(int,a.strip().split(',')) for a in img_data]
pixels = []
for p in img:
    pixels += p
faces = np.reshape(pixels,(400,4096))

######### Global Variable ##########

image_count = 0

######### Function that normalizes a vector x (i.e. |x|=1 ) #########

# > numpy.linalg.norm(x, ord=None, axis=None, keepdims=False) 
#   This function is able to return one of eight different matrix norms, 
#   or one of an infinite number of vector norms (described below), 
#   depending on the value of the ord parameter.
#   Note: in the given functionm, U should be a vector, not a array. 
#         You can write your own normalize function for normalizing 
#         the colomns of an array.

def normalize(U):
	return U / LA.norm(U) 

######### Display first face #########

# Useful functions:
# > numpy.reshape(a, newshape, order='C')
#   Gives a new shape to an array without changing its data.
# > matplotlib.pyplot.figure()
# 	Creates a new figure.
# > matplotlib.pyplot.title()
#	Set a title of the current axes.
# > matplotlib.pyplot.imshow()
#	Display an image on the axes.
#	Note: You need a matplotlib.pyplot.show() at the end to display all the figures.

    
#def compute_mean(faces):
#    mean_result = [0] * len(faces[0])
#    for i in range(len(faces)):
#        for j in range(len(faces[0])):
#            mean_result[j] += faces[i][j]
            
#    for i in range(len(mean_result)):
#        mean_result[i] = mean_result[i]/len(faces)
#    return mean_result

#mean_faces = compute_mean(faces)


#Initialising a zero matrix with dimensions 1 * 4096
mean_face = np.empty(shape=(4096,1))
mean_face = np.mean(faces, axis = 0)

#print(mean_face)



first_face = np.reshape(faces[99],(64,64),order='F')
image_count+=1
plt.figure(image_count)
plt.title('First_face')
plt.imshow(first_face,cmap=plt.cm.gray)


########## display a random face ###########

# Useful functions:
# > numpy.random.choice(a, size=None, replace=True, p=None)
#   Generates a random sample from a given 1-D array
# > numpy.ndarray.shape()
#   Tuple of array dimensions.
#   Note: There are two ways to order the elements in an array: 
#         column-major order and row-major order. In np.reshape(), 
#         you can switch the order by order='C' for row-major(default), 
#         or by order='F' for column-major. 


#### Your Code Here ####
def display_random_face():
    random_choice = np.random.choice(len(faces))
    random_face = np.reshape(faces[random_choice], (64,64), order="F")
    plt.figure(image_count)
    plt.title('Random face')
    plt.imshow(random_face,cmap=plt.cm.gray)
    
#display_random_face()


########## compute and display the mean face ###########

# Useful functions:
# > numpy.mean(a, axis='None', ...)
#   Compute the arithmetic mean along the specified axis.
#   Returns the average of the array elements. The average is taken over 
#   the flattened array by default, otherwise over the specified axis. 
#   float64 intermediate and return values are used for integer inputs.

#### Your Code Here ####
#def compute_mean(faces):
 #   mean_result = faces[0]
  #  for i in range(1, len(faces)):
   #     for j in range(len(faces[0])):
    #        mean_result[j] += faces[i][j]
            
    #for i in range(len(mean_result)):
     #   mean_result[i] = mean_result[i]/len(faces)
    #return mean_result
    

######### substract the mean from the face images and get the centralized data matrix A ###########

# Useful functions:
# > numpy.repeat(a, repeats, axis=None)
#   Repeat elements of an array.

#### Your Code Here ####
#Faces are being "cenetered"
faces=faces-mean_face
#print(faces[0])
        
faces_matrix = np.matrix(faces)
faces_transpose = faces_matrix.transpose()
#print(faces_transpose.shape)


#Covariance Matrix has been calculated using the calculation below
cov_matrix = np.matmul(faces_matrix, faces_transpose)

#print(cov_matrix)


######### calculate the eigenvalues and eigenvectors of the covariance matrix #####################

# Useful functions:
# > numpy.matrix()
#   Returns a matrix from an array-like object, or from a string of data. 
#   A matrix is a specialized 2-D array that retains its 2-D nature through operations. 
#   It has certain special operators, such as * (matrix multiplication) and ** (matrix power).

# > numpy.matrix.transpose(*axes)
#   Returns a view of the array with axes transposed.

# > numpy.linalg.eig(a)[source]
#   Compute the eigenvalues and right eigenvectors of a square array.
#   The eigenvalues, each repeated according to its multiplicity. 
#   The eigenvalues are not necessarily ordered. 

#### Your Code Here ####


#print(faces)
#print(len(cov_matrix))
#print(cov_matrix)
### EIGEN VALUES AND EIGEN VECTORS

#print(cov_matrix)
eigen_value, eigen_vector = np.linalg.eig(cov_matrix)



#print(eigen_vector)

# Eigen vector is a matrix with dimension 400 * 400. Each Eigen vector currently corresponds to 1 * 400 dimensions

eigen_v = []
for i in range(len(eigen_vector)):
    eigen_v.append(np.matmul(faces_transpose, eigen_vector[:,i]))
    
for i in range(len(eigen_v)):
    eigen_v[i] = normalize(eigen_v[i])
    
    
# eigen_v has a length of 400

#print(eigen_v[0].shape)
#print(faces[0])

#print(cov_matrix)

#print(eigen_v[0])
sorted_index = np.argsort(eigen_value)

sorted_index = sorted_index[:: -1]
total_variance = 0


for i in sorted_index:
    total_variance += eigen_value[i]

total_variance = total_variance/len(eigen_value)

p_c_variance = []
for i in sorted_index:
    p_c_variance.append(eigen_value[i]/total_variance)

#for each in p_c_variance:
    #print(each)

image_c = 0
for each in p_c_variance:
    image_c += 1
plt.figure(image_c)
plt.plot(p_c_variance)
plt.show()
    
#print(len(p_c_variance)) 
k_principal_components = []
for index in sorted_index[:400]:
    k_principal_components.append(eigen_v[index])

    
#print(k_principal_components[0])

#print(k_principal_components[0])

#k_p_c is a matrix with dimensions (400, 4096, 1)
    
#k_p_c = np.array(k_principal_components)
#print(k_p_c.shape)

#print(k_principal_components[:16])
#print(k_principal_components[0])

#s = eigen_vector[:399]
#s = s.transpose()

k = 399
U = k_principal_components[:k]

#print(len(U))
#print(U[0].shape)

U = np.matrix(np.array(U))

#U = U.transpose()
#print(U.shape)


first_2_pc = U

#print(first_2_pc.shape)
#print(np.matrix(faces[0]).transpose().shape)

omega = np.matmul(first_2_pc, np.matrix(faces[99]).transpose())
#omega = omega.transpose()
#print(omega.shape)

x_prime = np.matmul(first_2_pc.transpose(), omega)
#print(x_prime.shape)
#print(faces[0].shape)

#mean = []
#mean.append(mean_face)
#mean = np.array(mean)
#mean = mean.transpose()

mean_face = np.matrix(mean_face)
mean_face = mean_face.transpose()
#print(mean_face.shape)
final = np.add(mean_face, x_prime)
#print(final.shape)
#print(first_2_pc.shape)



first_face = np.reshape(final,(64,64),order='F')
image_count+=1
plt.figure(image_count)
plt.title('FINAL_face')
plt.imshow(first_face,cmap=plt.cm.gray)

#eigen_value = np.sort(eigen_value, axis = None)



#print(len(eig_val[0]), len(eig_vec[0]))


########## Display the first 16 principal components ##################

#### Your Code Here ####




########## Reconstruct the first face using the first two PCs #########

#### Your Code Here ####





########## Reconstruct random face using the first 5, 10, 25, 50, 100, 200, 300, 399  PCs ###########

#### Your Code Here ####



######### Plot proportion of variance of all the PCs ###############

# Useful functions:
# > matplotlib.pyplot.plot(*args, **kwargs)
#   Plot lines and/or markers to the Axes. 
# > matplotlib.pyplot.show(*args, **kw)
#   Display a figure. 
#   When running in ipython with its pylab mode, 
#   display all figures and return to the ipython prompt.

#### Your Code Here ####


