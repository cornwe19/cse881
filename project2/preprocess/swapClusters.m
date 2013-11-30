function result = swapClusters( orig, swaps )
	result = orig;
	n = max( orig );
	i = 1;
	for i = 1:2:size(swaps,2)
		result( result == swaps(i) ) = n+1;
		result( result == swaps(i+1) ) = swaps( i );
		result( result == n+1 ) = swaps( i + 1 );
	end
