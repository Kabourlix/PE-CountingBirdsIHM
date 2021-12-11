import matplotlib.pyplot as plt
import numpy as np

x = np.linspace(0,200,2000)
y = 5*x

plt.plot(x,y)
plt.savefig("salut")
