from __future__ import division
import sys

def main():

	states = [0.6, -0.3, -0.5, 0.8]
	alpha = 0.01
	gamma = 1

	for i in range(100000):
		#Episode 1
		states[1] = states[1] + alpha * (1 - states[1])

		#Episode 2
		states[2] = states[2] + alpha * (-1 - states[2])

		#Episode 3
		states[0] = states[0] + alpha * (0 + (gamma * states[3]) - states[0])
		states[3] = states[3] + alpha * (0 - states[3])

		print states

if __name__ == '__main__':
	main()