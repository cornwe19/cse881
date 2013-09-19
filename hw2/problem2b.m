disp( 'Loading data into matrix A...' );
A = load( 'data/2d_nonlinear.txt' );
 
figure;
hold on;
scatter( A( 1:300, 1 ), A( 1:300, 2 ), 'red.' );
scatter( A( 301:600, 1 ), A( 301:600, 2 ), 'blue*' );
hold off;

% Compute the kernel PCA to differentiate nonlinear dataset
n = size( A, 1 );
varA = var( A );

% Use RBF distance function with variance computed on the euclidean distance between points
rbf = @( xi, xj ) exp( -( 1 / 2 * var( pdist2( xi, xj, 'euclidean' ) ) ) *  pdist2( xi, xj, 'euclidean' ).^2 );

D = pdist( A, rbf );
Z = squareform( D );
Znorm = Z ./ n;

[V,D] = eigs( Znorm, 1 );

proj1 = A( 1:300, : )' * V( 1:300, : );
proj2 = A( 301:600, : )' * V( 301:600, : );

figure;
hold on;

hist( proj1 );
hist( proj2 );

h = findobj(gca,'Type','patch');

set(h(1),'FaceColor','r','EdgeColor','r');
set(h(2),'FaceColor','b','EdgeColor','b');

hold off;

% Compute the linear PCA to compare
[Vl,Dl] = eigs( cov( A ), 1 );

projL1 = A( 1:300, : ) * Vl;
projL2 = A( 301:600, : ) * Vl;

figure;
hold on;

hist( projL1, 100 );
hist( projL2, 100 );

h = findobj(gca,'Type','patch');

set(h(1),'FaceColor','r','EdgeColor','r');
set(h(2),'FaceColor','b','EdgeColor','b');

hold off;
