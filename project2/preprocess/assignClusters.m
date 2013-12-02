function index = assignClusters( orig, clusters )
	% usage index = assignClusters( original_data, cluster_centroids )
	[vals,index] = min( sqrt( bsxfun( @plus, sum( orig.^2, 2 ), sum( clusters.^2, 2 )' ) - 2 * ( orig * clusters' ) ), [], 2 );

