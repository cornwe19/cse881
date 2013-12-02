function centroids = makeCentroids( indices, orig )
	% Make centroids from a set of indices and original data
	%
	% indices must be a column vector
	u = unique( indices );
	for i = u'
		centroids(i, :) = mean( orig( indices == i, : ) );
	end

