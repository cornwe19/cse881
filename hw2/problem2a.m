disp( 'Loading data into matrix A...' );
A = load( 'data/2d_linear.txt' );
newData = [0.0020 0.2223; 0.2642 0.3190; 0.5061 0.7418; 0.8912 0.8517];

figure;
hold on;
scatter( A(:,1), A(:,2), 'red.');
scatter( newData(:,1), newData(:,2), 'blue*' );

[V,D] = eigs( cov( A ), 1 );

projA = A * V;


disp( 'New data projected onto 1st principal component' );
newData * V

disp( 'Variance of the projection of A onto 1st principal component' );
var( projA )

disp( 'Variance of projection of A onto the X axis' );
var( A(:,1) )
