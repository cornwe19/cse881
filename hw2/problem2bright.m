A = load('data/2d_nonlinear.txt');
d = pdist(A);	    % compute Euclidean distance
sigma = std(d);     % compute sigma 

N = size(A,1);	    % get number of data points
K = exp(-d.^2/sigma^2);
K = squareform(K) + eye(N);	 % make sure diagnal elements are 1s
K = K/N;

[V,D] = eigs(K,1);    % compute the first eigenvector

p = K*V;	      % compute the first principal component

h = figure();
hist(p(1:N/2),10);
hold;
hist(p(N/2+1:end),10);
