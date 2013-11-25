links = load( 'pruned0601.txt' );

link_vals = ones( size( links, 1 ), 1 );

links = horzcat( links, link_vals );

links = spconvert( links );

links = links( sum( links, 2 ) > 0, : );

disp( sprintf( 'Using %d link nodes', size( links, 1 ) ) );

disp( 'Starting clustering' );

opts = statset( 'Display','iter', 'MaxIter', 15, 'UseParallel', 'always'  );
[index, clusters] = kmeans( links( 1:5000, : ), 10, 'Options', opts, 'onlinephase', 'off', 'emptyaction', 'singleton' );

% Find the counts from clustering
u = unique( index(:,1) );
counts = arrayfun( @(y) length( index( index( :, 1 ) == y ) ), u );

assignments = horzcat( u, counts );

disp( 'Initial cluster sizes' );
disp( assignments );

