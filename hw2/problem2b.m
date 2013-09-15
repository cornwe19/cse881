disp( 'Loading data into matrix A...' );
A = load( 'data/2d_nonlinear.txt' );

figure;
hold on;
scatter( A( 1:300, 1 ), A( 1:300, 2 ), 'red.' );
scatter( A( 301:600, 1 ), A( 301:600, 2 ), 'blue*' );
hold off;

n = size( A, 1 );
varA = var( A );

rbf = @( xi, xj ) exp( -1 * ( bsxfun( @minus, xi, xj ).^2 ) / ( 2 * varA ) );

D = pdist( A, rbf );
Z = squareform( D );
Znorm = Z ./ n;

[V,D] = eigs( Znorm, 1 );

proj1 = A( 1:300, : )' * Znorm( 1:300, : );
proj2 = A( 301:600, : )' * Znorm( 301:600, : );

figure;
hold on;
hist( proj1', 300 );
hist( proj2', 300 );

h = findobj(gca,'Type','patch');

set(h(1),'FaceColor','r','EdgeColor','r');
set(h(2),'FaceColor','b','EdgeColor','b');

hold off;
