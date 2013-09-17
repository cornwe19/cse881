A = load( 'data/2d_data.txt' );

X = A(:,1:2);
Y = A(:,3);

lambda = 0.05;

% NxN diagonal with all weights set to 1 except the last one.
alpha = diag( [ones( size(X,1) - 1, 1.000 ); 0.001] ); 

w = inv( X' * X + lambda * eye(size(X,2)) ) * X' * Y;
wWeighted = inv( X' * alpha * X + lambda * eye(size(X,2)) ) * X' * alpha  * Y;

disp( 'Values of unweighted and weighted coefficients' );

w
wWeighted

