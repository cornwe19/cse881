for i = 5001:size(links,1)
	[val, index(i)] = min( sum( bsxfun( @minus, clusters, links(i,:) ).^2, 2 ) );
	disp( sprintf( 'Assigning %d to %d', i, index(i) ) );
end
