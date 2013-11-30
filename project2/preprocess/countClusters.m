function countClusters( clIndex )
	% Display the cluster counts for the given cluster index
	%
	% clIndex must be a column vector of cluster assignments
	u = unique( clIndex(:,1) );
	counts = arrayfun( @(y) length( clIndex( clIndex( :, 1 ) == y ) ), u );

	assignments = horzcat( u, counts );

	disp( 'Cluster sizes' );
	disp( assignments );

